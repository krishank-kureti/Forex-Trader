export interface User {
  id: string;
  email: string;
  name: string;
  createdAt: string;
}

export interface Wallet {
  id: string;
  userId: string;
  balance: number;
  currency: string;
  createdAt: string;
  updatedAt: string;
}

export interface Trade {
  id: string;
  userId: string;
  type: 'BUY' | 'SELL';
  pair: string;
  amount: number;
  rate: number;
  status: 'PENDING' | 'COMPLETED' | 'FAILED';
  createdAt: string;
  settledAt?: string;
}

export interface ExchangeRate {
  pair: string;
  bid: number;
  ask: number;
  timestamp: string;
}

export interface Settlement {
  id: string;
  tradeId: string;
  amount: number;
  status: 'PENDING' | 'PROCESSED' | 'FAILED';
  processedAt?: string;
  createdAt: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  name: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface TradeRequest {
  type: 'BUY' | 'SELL';
  pair: string;
  amount: number;
}