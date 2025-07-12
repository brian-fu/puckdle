import { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Card } from '@/components/ui/card';
import { Search, Loader2 } from 'lucide-react';

interface GuessInputProps {
  onSubmit: (playerName: string) => void;
  disabled?: boolean;
  loading?: boolean;
}

const GuessInput = ({ onSubmit, disabled = false, loading = false }: GuessInputProps) => {
  const [playerName, setPlayerName] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (playerName.trim() && !disabled && !loading) {
      onSubmit(playerName.trim());
      setPlayerName('');
    }
  };

  return (
    <Card className="p-4 bg-card shadow-card border-border">
      <form onSubmit={handleSubmit} className="flex gap-3">
        <div className="flex-1 relative">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-4 w-4" />
          <Input
            type="text"
            placeholder="Enter NHL player name..."
            value={playerName}
            onChange={(e) => setPlayerName(e.target.value)}
            disabled={disabled || loading}
            className="pl-10 bg-input border-border focus:ring-primary focus:border-primary"
          />
        </div>
        <Button 
          type="submit" 
          disabled={!playerName.trim() || disabled || loading}
          className="bg-primary hover:bg-primary/90 text-primary-foreground px-6 transition-all duration-200"
        >
          {loading ? (
            <>
              <Loader2 className="mr-2 h-4 w-4 animate-spin" />
              Guessing...
            </>
          ) : (
            'Guess'
          )}
        </Button>
      </form>
    </Card>
  );
};

export default GuessInput;