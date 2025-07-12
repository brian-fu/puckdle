import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { gameAPI, GuessHistory as GuessHistoryType } from '@/lib/api';
import { useToast } from '@/hooks/use-toast';
import GameHeader from '@/components/GameHeader';
import GuessInput from '@/components/GuessInput';
import GuessHistory from '@/components/GuessHistory';
import FeedbackLegend from '@/components/FeedbackLegend';

const GamePage = () => {
  const [sessionId, setSessionId] = useState<string>('');
  const [guesses, setGuesses] = useState<GuessHistoryType[]>([]);
  const [loading, setLoading] = useState(false);
  const [gameWon, setGameWon] = useState(false);
  const navigate = useNavigate();
  const { toast } = useToast();

  useEffect(() => {
    // Get session ID from localStorage
    const storedSessionId = localStorage.getItem('puckdle-session');
    if (!storedSessionId) {
      // No session found, redirect to home
      navigate('/');
      return;
    }
    setSessionId(storedSessionId);
  }, [navigate]);

  const handleGuess = async (playerName: string) => {
    if (!sessionId) return;

    setLoading(true);
    try {
      const response = await gameAPI.submitGuess(sessionId, playerName);
      
      const newGuess: GuessHistoryType = {
        player: response.guessedPlayer,
        feedbackList: response.feedbackList,
      };
      
      setGuesses(prev => [...prev, newGuess]);

      // Check if all attributes are correct (game won)
      const allCorrect = response.feedbackList.every(
        feedback => feedback.feedbackType === 'CORRECT'
      );
      
      if (allCorrect) {
        setGameWon(true);
        toast({
          title: "🎉 Congratulations!",
          description: `You guessed it in ${guesses.length + 1} tries!`,
        });
      }

    } catch (error: any) {
      console.error('Failed to submit guess:', error);
      
      // Handle specific error cases
      if (error.response?.status === 404) {
        toast({
          title: "Player Not Found",
          description: "Please check the spelling and try again.",
          variant: "destructive",
        });
      } else if (error.response?.status === 400) {
        toast({
          title: "Invalid Session",
          description: "Your game session has expired. Starting a new game.",
          variant: "destructive",
        });
        handleNewGame();
      } else {
        toast({
          title: "Error",
          description: "Failed to submit guess. Please try again.",
          variant: "destructive",
        });
      }
    } finally {
      setLoading(false);
    }
  };

  const handleNewGame = async () => {
    try {
      const response = await gameAPI.startGame();
      localStorage.setItem('puckdle-session', response.sessionId);
      setSessionId(response.sessionId);
      setGuesses([]);
      setGameWon(false);
      toast({
        title: "New Game Started",
        description: "Good luck with your next challenge!",
      });
    } catch (error) {
      console.error('Failed to start new game:', error);
      toast({
        title: "Error",
        description: "Failed to start new game. Please try again.",
        variant: "destructive",
      });
    }
  };

  if (!sessionId) {
    return (
      <div className="min-h-screen bg-gradient-subtle flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary mx-auto mb-4"></div>
          <p className="text-muted-foreground">Loading game...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-subtle">
      <div className="max-w-6xl mx-auto p-4 space-y-6">
        {/* Game Header */}
        <GameHeader
          guessCount={guesses.length}
          onNewGame={handleNewGame}
          gameWon={gameWon}
        />

        <div className="grid lg:grid-cols-4 gap-6">
          {/* Main Game Area */}
          <div className="lg:col-span-3 space-y-6">
            {/* Guess Input */}
            <GuessInput
              onSubmit={handleGuess}
              disabled={gameWon}
              loading={loading}
            />

            {/* Guess History */}
            <GuessHistory guesses={guesses} />
          </div>

          {/* Sidebar */}
          <div className="space-y-6">
            <FeedbackLegend />
            
            {/* Game Stats */}
            <div className="bg-card p-4 rounded-lg shadow-card border-border">
              <h3 className="text-sm font-semibold text-foreground mb-3">
                Game Stats
              </h3>
              <div className="space-y-2 text-sm">
                <div className="flex justify-between">
                  <span className="text-muted-foreground">Guesses:</span>
                  <span className="font-medium text-foreground">{guesses.length}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-muted-foreground">Status:</span>
                  <span className={`font-medium ${gameWon ? 'text-correct' : 'text-foreground'}`}>
                    {gameWon ? 'Won!' : 'In Progress'}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default GamePage;