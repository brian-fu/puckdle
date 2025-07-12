import { Button } from '@/components/ui/button';
import { Card } from '@/components/ui/card';
import { Trophy, RotateCcw, Zap } from 'lucide-react';

interface GameHeaderProps {
  guessCount: number;
  onNewGame: () => void;
  gameWon?: boolean;
}

const GameHeader = ({ guessCount, onNewGame, gameWon = false }: GameHeaderProps) => {
  return (
    <Card className="p-4 bg-gradient-ice text-primary-foreground shadow-ice border-primary/20">
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-3">
          <div className="p-2 bg-white/20 rounded-lg">
            <Zap className="h-6 w-6" />
          </div>
          <div>
            <h1 className="text-2xl font-bold">Puckdle</h1>
            <p className="text-sm text-primary-foreground/80">
              Guess the NHL player!
            </p>
          </div>
        </div>
        
        <div className="flex items-center gap-4">
          {gameWon && (
            <div className="flex items-center gap-2 text-sm">
              <Trophy className="h-4 w-4" />
              <span>Winner!</span>
            </div>
          )}
          
          <div className="text-right">
            <div className="text-sm text-primary-foreground/80">Guesses</div>
            <div className="text-xl font-bold">{guessCount}</div>
          </div>
          
          <Button
            onClick={onNewGame}
            variant="outline"
            size="sm"
            className="bg-white/10 border-white/20 text-primary-foreground hover:bg-white/20"
          >
            <RotateCcw className="h-4 w-4 mr-2" />
            New Game
          </Button>
        </div>
      </div>
    </Card>
  );
};

export default GameHeader;