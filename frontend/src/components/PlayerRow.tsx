import { Player, PlayerFeedbackDTO } from '@/lib/api';
import { Card } from '@/components/ui/card';

interface PlayerRowProps {
  player: Player;
  feedbackList: PlayerFeedbackDTO[];
  isLatest?: boolean;
}

const PlayerRow = ({ player, feedbackList, isLatest = false }: PlayerRowProps) => {
  // Create a map for quick feedback lookup
  const feedbackMap = feedbackList.reduce((acc, feedback) => {
    acc[feedback.attribute] = feedback.feedbackType;
    return acc;
  }, {} as Record<string, string>);

  // Helper function to get cell styling based on feedback
  const getCellStyle = (attribute: string) => {
    const feedback = feedbackMap[attribute];
    switch (feedback) {
      case 'CORRECT':
        return 'bg-correct text-correct-foreground';
      case 'CLOSE':
        return 'bg-close text-close-foreground';
      case 'INCORRECT':
        return 'bg-incorrect text-incorrect-foreground';
      default:
        return 'bg-muted text-muted-foreground';
    }
  };

  // Attribute order and display
  const attributes = [
    { key: 'fullName', value: player.fullName, label: 'Name' },
    { key: 'age', value: player.age, label: 'Age' },
    { key: 'jerseyNumber', value: player.jerseyNumber, label: 'Number' },
    { key: 'teamAbbr', value: player.teamAbbr, label: 'Team' },
    { key: 'division', value: player.division, label: 'Division' },
    { key: 'positionCode', value: player.positionCode, label: 'Position' },
    { key: 'nationality', value: player.nationality, label: 'Nationality' },
    { key: 'shoots', value: player.shoots, label: 'Shoots' },
  ];

  return (
    <Card className={`overflow-hidden shadow-card border-border ${isLatest ? 'animate-slide-up' : ''}`}>
      <div className="grid grid-cols-8 text-xs">
        {attributes.map((attr, index) => (
          <div
            key={attr.key}
            className={`
              p-3 text-center font-medium border-r border-border last:border-r-0
              ${getCellStyle(attr.key)}
              ${isLatest ? `animate-flip-in` : ''}
            `}
            style={isLatest ? { animationDelay: `${index * 100}ms` } : {}}
          >
            <div className="hidden sm:block text-xs opacity-75 mb-1">
              {attr.label}
            </div>
            <div className="font-semibold">
              {attr.value}
            </div>
          </div>
        ))}
      </div>
    </Card>
  );
};

export default PlayerRow;