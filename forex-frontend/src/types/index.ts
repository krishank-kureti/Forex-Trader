// Authentication Types
export interface User {
 id: number;
 username: string;
 email: string;
 role: 'TRADER' | 'ADMIN';
}
export interface AuthResponse {
 token: string;
 username: string;
 role: string;
}
export interface LoginRequest {
 username: string;
 password: string;
}
export interface RegisterRequest {
 username: string;
 email: string;
 password: string;
 role?: 'TRADER' | 'ADMIN';
}
// Wallet Types
export interface WalletRequest {
 amount: number;
 password?: string;
}
export interface WalletResponse {
 walletId: number;
 balance: number;
 currency: string;
 status: 'SUCCESS' | 'FAILED' | 'PENDING';
 message?: string;
}
export interface Transaction {
 id: number;
 walletId: number;
 type: 'DEPOSIT' | 'WITHDRAWAL';
 amount: number;
 status: 'SUCCESS' | 'FAILED' | 'PENDING';
 timestamp: string;
 message?: string;
}
// Trade Types
export interface OrderRequest {
 currencyPairId: number;
 type: 'BUY' | 'SELL';
 amount: number;
 price?: number;
}
export interface OrderResponse {
 orderId: number;
 currencyPair: string;
 type: 'BUY' | 'SELL';
 amount: number;
 price?: number;
 status: string;
 createdAt: string;
}
// Exchange Rate Types
export interface ExchangeRates {
 [pair: string]: number;
}
// Settlement Types
export interface SettlementResponse {
 settlementId: number;
 tradeId: number;
 amount: number;
 fee: number;
 status: string;
 settledAt: string;
}
// Trade History Types
export interface TradeHistoryResponse {
 id: number;
 tradeId: number;
 type: string;
 currencyPair: string;
 amount: number;
 price: number;
 pnl?: number;
 timestamp: string;
}
