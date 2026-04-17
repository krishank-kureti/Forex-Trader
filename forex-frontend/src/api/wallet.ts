import client from './client';
import { Wallet } from '../types';

export const walletApi = {
  getBalance: async (): Promise<Wallet> => {
    const response = await client.get('/wallet/balance');
    return response.data;
  },

  deposit: async (amount: number): Promise<Wallet> => {
    const response = await client.post('/wallet/deposit', { amount });
    return response.data;
  },

  withdraw: async (amount: number): Promise<Wallet> => {
    const response = await client.post('/wallet/withdraw', { amount });
    return response.data;
  },

  getTransactions: async () => {
    const response = await client.get('/wallet/transactions');
    return response.data;
  },
};