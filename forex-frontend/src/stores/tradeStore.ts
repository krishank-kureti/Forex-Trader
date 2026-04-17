import { create } from 'zustand';
import { Trade } from '../types';

interface TradeState {
  trades: Trade[];
  currentTrade: Trade | null;
  isLoading: boolean;
  setTrades: (trades: Trade[]) => void;
  addTrade: (trade: Trade) => void;
  setCurrentTrade: (trade: Trade | null) => void;
  setLoading: (loading: boolean) => void;
}

export const useTradeStore = create<TradeState>((set) => ({
  trades: [],
  currentTrade: null,
  isLoading: false,
  setTrades: (trades) => set({ trades }),
  addTrade: (trade) => set((state) => ({ trades: [trade, ...state.trades] })),
  setCurrentTrade: (trade) => set({ currentTrade: trade }),
  setLoading: (isLoading) => set({ isLoading }),
}));