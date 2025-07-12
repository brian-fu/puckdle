import axios from 'axios';

// Configure axios with base URL - update this to match your backend
const api = axios.create({
  baseURL: process.env.NODE_ENV === 'production' 
    ? 'https://your-backend-url.com/api'  // Replace with actual production URL
    : 'http://localhost:8080/api',        // Local development URL
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export interface Player {
  fullName: string;
  age: number;
  jerseyNumber: number;
  teamAbbr: string;
  division: string;
  positionCode: string;
  shoots: string;
  nationality: string;
}

export interface PlayerFeedbackDTO {
  attribute: string;
  feedbackType: 'CORRECT' | 'CLOSE' | 'INCORRECT';
  guessedValue: any;
}

export interface StartGameResponse {
  sessionId: string;
}

export interface GuessResponse {
  sessionId: string;
  guessedPlayer: Player;
  feedbackList: PlayerFeedbackDTO[];
}

export interface GuessHistory {
  player: Player;
  feedbackList: PlayerFeedbackDTO[];
}

// API functions
export const gameAPI = {
  async startGame(): Promise<StartGameResponse> {
    const response = await api.post<StartGameResponse>('/game/start');
    return response.data;
  },

  async submitGuess(sessionId: string, playerName: string): Promise<GuessResponse> {
    const response = await api.post<GuessResponse>(`/game/${sessionId}/guess`, {
      playerName,
    });
    return response.data;
  },
};

export default api;