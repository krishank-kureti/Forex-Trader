import client from './client';
import { LoginRequest, RegisterRequest, AuthResponse } from '../types';

export const authApi = {
  login: async (data: LoginRequest): Promise<AuthResponse> => {
    const response = await client.post('/auth/login', data);
    return response.data;
  },

  register: async (data: RegisterRequest): Promise<AuthResponse> => {
    const response = await client.post('/auth/register', data);
    return response.data;
  },

  logout: async (): Promise<void> => {
    await client.post('/auth/logout');
  },

  me: async () => {
    const response = await client.get('/auth/me');
    return response.data;
  },
};