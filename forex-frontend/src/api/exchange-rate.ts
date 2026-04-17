import client from './client';
import { ExchangeRate } from '../types';

export const exchangeRateApi = {
  getRates: async (): Promise<ExchangeRate[]> => {
    const response = await client.get('/exchange-rates');
    return response.data;
  },

  getRate: async (pair: string): Promise<ExchangeRate> => {
    const response = await client.get(`/exchange-rates/${pair}`);
    return response.data;
  },
};