import { GuessHistory as GuessHistoryType } from '@/lib/api';
import PlayerRow from './PlayerRow';
import { Card } from '@/components/ui/card';

interface GuessHistoryProps {
  guesses: GuessHistoryType[];
}

const GuessHistory = ({ guesses }: GuessHistoryProps) => {
  if (guesses.length === 0) {
    return (
      <Card className="p-8 text-center bg-muted/30 border-dashed border-2 border-border">
        <p className="text-muted-foreground">
          Make your first guess to start the game!
        </p>
      </Card>
    );
  }

  return (
    <div className="space-y-3">
      {/* Header row */}
      <Card className="bg-secondary shadow-card border-border">
        <div className="grid grid-cols-8 text-xs font-semibold text-secondary-foreground">
          {['Name', 'Age', 'Number', 'Team', 'Division', 'Position', 'Nationality', 'Shoots'].map((header) => (
            <div key={header} className="p-3 text-center border-r border-border last:border-r-0">
              {header}
            </div>
          ))}
        </div>
      </Card>

      {/* Guess rows */}
      {guesses.map((guess, index) => (
        <PlayerRow
          key={index}
          player={guess.player}
          feedbackList={guess.feedbackList}
          isLatest={index === guesses.length - 1}
        />
      ))}
    </div>
  );
};

export default GuessHistory;