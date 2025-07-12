import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card } from '@/components/ui/card';
import { gameAPI } from '@/lib/api';
import { useToast } from '@/hooks/use-toast';
import { Zap, Play, Trophy, Target, Users } from 'lucide-react';

const HomePage = () => {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { toast } = useToast();

  const handleStartGame = async () => {
    setLoading(true);
    try {
      const response = await gameAPI.startGame();
      // Store session ID in localStorage for now
      localStorage.setItem('puckdle-session', response.sessionId);
      navigate('/game');
    } catch (error) {
      console.error('Failed to start game:', error);
      toast({
        title: "Connection Error",
        description: "Unable to connect to game server. Please try again.",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-subtle flex items-center justify-center p-4">
      <div className="max-w-2xl w-full space-y-8">
        {/* Hero Section */}
        <div className="text-center space-y-4">
          <div className="flex justify-center">
            <div className="p-4 bg-primary rounded-2xl shadow-ice animate-pulse-glow">
              <Zap className="h-12 w-12 text-primary-foreground" />
            </div>
          </div>
          
          <h1 className="text-6xl font-bold text-foreground">
            <span className="bg-gradient-ice bg-clip-text text-transparent">
              Puckdle
            </span>
          </h1>
          
          <p className="text-xl text-muted-foreground max-w-md mx-auto">
            The daily NHL player guessing game. Like Wordle, but for hockey fans!
          </p>
        </div>

        {/* Game Features */}
        <div className="grid md:grid-cols-3 gap-4">
          <Card className="p-4 text-center bg-card shadow-card border-border">
            <Target className="h-8 w-8 text-primary mx-auto mb-3" />
            <h3 className="font-semibold text-foreground mb-2">Daily Challenge</h3>
            <p className="text-sm text-muted-foreground">
              Guess the mystery NHL player with strategic clues
            </p>
          </Card>
          
          <Card className="p-4 text-center bg-card shadow-card border-border">
            <Trophy className="h-8 w-8 text-primary mx-auto mb-3" />
            <h3 className="font-semibold text-foreground mb-2">Smart Hints</h3>
            <p className="text-sm text-muted-foreground">
              Age, team, position, and more clues guide your guesses
            </p>
          </Card>
          
          <Card className="p-4 text-center bg-card shadow-card border-border">
            <Users className="h-8 w-8 text-primary mx-auto mb-3" />
            <h3 className="font-semibold text-foreground mb-2">All Players</h3>
            <p className="text-sm text-muted-foreground">
              Current and past NHL players from all eras
            </p>
          </Card>
        </div>

        {/* How to Play */}
        <Card className="p-6 bg-card shadow-card border-border">
          <h2 className="text-xl font-semibold text-foreground mb-4">How to Play</h2>
          <div className="space-y-3 text-sm text-muted-foreground">
            <div className="flex items-start gap-3">
              <div className="w-6 h-6 bg-primary rounded-full flex items-center justify-center text-primary-foreground text-xs font-bold flex-shrink-0 mt-0.5">
                1
              </div>
              <p>Guess any current or former NHL player by typing their name</p>
            </div>
            <div className="flex items-start gap-3">
              <div className="w-6 h-6 bg-primary rounded-full flex items-center justify-center text-primary-foreground text-xs font-bold flex-shrink-0 mt-0.5">
                2
              </div>
              <p>See how your guess compares to the mystery player across 8 attributes</p>
            </div>
            <div className="flex items-start gap-3">
              <div className="w-6 h-6 bg-primary rounded-full flex items-center justify-center text-primary-foreground text-xs font-bold flex-shrink-0 mt-0.5">
                3
              </div>
              <p>Use the color-coded feedback to narrow down your next guess</p>
            </div>
          </div>
          
          <div className="mt-6 flex justify-center gap-4 text-xs">
            <div className="flex items-center gap-2">
              <div className="w-4 h-4 bg-correct rounded"></div>
              <span>Correct</span>
            </div>
            <div className="flex items-center gap-2">
              <div className="w-4 h-4 bg-close rounded"></div>
              <span>Close</span>
            </div>
            <div className="flex items-center gap-2">
              <div className="w-4 h-4 bg-incorrect rounded"></div>
              <span>Wrong</span>
            </div>
          </div>
        </Card>

        {/* Start Game Button */}
        <div className="text-center">
          <Button
            onClick={handleStartGame}
            disabled={loading}
            size="lg"
            className="bg-primary hover:bg-primary/90 text-primary-foreground px-8 py-4 text-lg font-semibold shadow-ice transition-all duration-200 hover:shadow-lg"
          >
            {loading ? (
              <>
                <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-primary-foreground mr-3"></div>
                Starting Game...
              </>
            ) : (
              <>
                <Play className="mr-3 h-5 w-5" />
                Start Playing
              </>
            )}
          </Button>
        </div>
      </div>
    </div>
  );
};

export default HomePage;