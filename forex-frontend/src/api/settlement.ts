import client from './client';
import { Settlement } from '../types';

export const settlementApi = {
  getSettlements: async (): Promise<Settlement[]> => {
    const response = await client.get('/settlements');
    return response.data;
  },

  processSettlement: async (tradeId: string): Promise<Settlement> => {
    const response = await client.post(`/settlements/process/${tradeId}`);
    return response.data;
  },
};