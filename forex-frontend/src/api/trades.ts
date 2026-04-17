import client from './client';
import { Trade, TradeRequest } from '../types';

export const tradesApi = {
  getTrades: async (): Promise<Trade[]> => {
    const response = await client.get('/trades');
    return response.data;
  },

  createTrade: async (data: TradeRequest): Promise<Trade> => {
    const response = await client.post('/trades', data);
    return response.data;
  },

  getTrade: async (id: string): Promise<Trade> => {
    const response = await client.get(`/trades/${id}`);
    return response.data;
  },
};