import React, { useState } from 'react';
import GlassCard from '../components/common/GlassCard';
import { useWallet } from '../hooks/useWallet';

const WalletPage: React.FC = () => {
  const { balance, transactions, isLoading } = useWallet();
  const [amount, setAmount] = useState('');
  const [activeTab, setActiveTab] = useState<'deposit' | 'withdraw'>('deposit');

  return (
    <div className="space-y-6">
      <h1 className="font-headline text-2xl font-bold text-text-primary">Wallet</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <GlassCard>
          <p className="text-text-muted text-sm">Available Balance</p>
          <p className="font-headline text-4xl font-bold text-text-primary mt-2">
            ${balance?.balance?.toFixed(2) || '0.00'}
          </p>
          <p className="text-text-muted text-xs mt-4">Currency: {balance?.currency || 'USD'}</p>
        </GlassCard>

        <GlassCard>
          <h2 className="font-headline text-lg font-bold text-text-primary mb-4">Quick Transfer</h2>
          <div className="flex gap-2 mb-4">
            <button
              onClick={() => setActiveTab('deposit')}
              className={`flex-1 py-2 text-sm font-bold transition-colors ${
                activeTab === 'deposit'
                  ? 'bg-accent-green text-bg-primary'
                  : 'bg-bg-surface text-text-secondary'
              }`}
            >
              DEPOSIT
            </button>
            <button
              onClick={() => setActiveTab('withdraw')}
              className={`flex-1 py-2 text-sm font-bold transition-colors ${
                activeTab === 'withdraw'
                  ? 'bg-accent-green text-bg-primary'
                  : 'bg-bg-surface text-text-secondary'
              }`}
            >
              WITHDRAW
            </button>
          </div>
          <input
            type="number"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            className="input-flat"
            placeholder="Enter amount"
          />
          <button className="w-full mt-4 bg-accent-green text-bg-primary font-headline font-bold py-3 hover:opacity-90 transition-opacity">
            {activeTab === 'deposit' ? 'DEPOSIT FUNDS' : 'WITHDRAW FUNDS'}
          </button>
        </GlassCard>
      </div>

      <GlassCard>
        <h2 className="font-headline text-lg font-bold text-text-primary mb-4">Transaction History</h2>
        {isLoading ? (
          <p className="text-text-muted">Loading...</p>
        ) : transactions && transactions.length > 0 ? (
          <div className="space-y-3">
            {transactions.map((tx: any) => (
              <div key={tx.id} className="flex items-center justify-between py-3 border-b border-border-subtle">
                <div>
                  <p className="text-text-primary text-sm">{tx.type}</p>
                  <p className="text-text-muted text-xs">{new Date(tx.createdAt).toLocaleString()}</p>
                </div>
                <div className="text-right">
                  <p className={`text-sm font-mono ${tx.type === 'DEPOSIT' ? 'text-accent-green' : 'text-accent-red'}`}>
                    {tx.type === 'DEPOSIT' ? '+' : '-'}${tx.amount.toFixed(2)}
                  </p>
                  <span className={`status-badge ${tx.status.toLowerCase()} text-xs`}>
                    {tx.status}
                  </span>
                </div>
              </div>
            ))}
          </div>
        ) : (
          <p className="text-text-muted text-sm">No transactions yet</p>
        )}
      </GlassCard>
    </div>
  );
};

export default WalletPage;