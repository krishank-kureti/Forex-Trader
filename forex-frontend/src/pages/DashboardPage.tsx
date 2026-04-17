import React from 'react';
import { Link } from 'react-router-dom';
import GlassCard from '../components/common/GlassCard';
import { useWallet } from '../hooks/useWallet';
import { useTrading } from '../hooks/useTrading';
import { useExchangeRates } from '../hooks/useExchangeRates';

const DashboardPage: React.FC = () => {
  const { balance } = useWallet();
  const { trades } = useTrading();
  const { rates } = useExchangeRates();

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="font-headline text-2xl font-bold text-text-primary">Dashboard</h1>
        <div className="flex items-center gap-2">
          <span className="status-dot online w-2 h-2 rounded-full"></span>
          <span className="text-text-muted text-sm">Live</span>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <GlassCard>
          <p className="text-text-muted text-sm">Total Balance</p>
          <p className="font-headline text-3xl font-bold text-text-primary mt-2">
            ${balance?.balance?.toFixed(2) || '0.00'}
          </p>
          <p className="text-text-muted text-xs mt-2">{balance?.currency || 'USD'}</p>
        </GlassCard>

        <GlassCard>
          <p className="text-text-muted text-sm">Active Trades</p>
          <p className="font-headline text-3xl font-bold text-text-primary mt-2">
            {trades?.filter((t) => t.status === 'PENDING').length || 0}
          </p>
          <Link to="/trades" className="text-accent-green text-xs mt-2 block hover:underline">
            View all trades →
          </Link>
        </GlassCard>

        <GlassCard>
          <p className="text-text-muted text-sm">Exchange Rates</p>
          <div className="space-y-2 mt-2">
            {rates?.slice(0, 3).map((rate) => (
              <div key={rate.pair} className="flex justify-between text-sm">
                <span className="text-text-secondary">{rate.pair}</span>
                <span className="text-text-primary font-mono">{rate.bid.toFixed(4)}</span>
              </div>
            ))}
            {(!rates || rates.length === 0) && (
              <p className="text-text-muted text-sm">Loading rates...</p>
            )}
          </div>
        </GlassCard>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <GlassCard>
          <h2 className="font-headline text-lg font-bold text-text-primary mb-4">Recent Trades</h2>
          <div className="space-y-3">
            {trades?.slice(0, 5).map((trade) => (
              <div key={trade.id} className="flex items-center justify-between py-2 border-b border-border-subtle">
                <div>
                  <span className={`text-sm font-bold ${trade.type === 'BUY' ? 'text-accent-green' : 'text-accent-red'}`}>
                    {trade.type}
                  </span>
                  <span className="text-text-secondary text-sm ml-2">{trade.pair}</span>
                </div>
                <div className="text-right">
                  <p className="text-text-primary text-sm font-mono">${trade.amount.toFixed(2)}</p>
                  <span className={`status-badge ${trade.status.toLowerCase()} text-xs`}>
                    {trade.status}
                  </span>
                </div>
              </div>
            ))}
            {(!trades || trades.length === 0) && (
              <p className="text-text-muted text-sm">No trades yet</p>
            )}
          </div>
        </GlassCard>

        <GlassCard>
          <h2 className="font-headline text-lg font-bold text-text-primary mb-4">Quick Actions</h2>
          <div className="grid grid-cols-2 gap-4">
            <Link
              to="/trades"
              className="card-flat p-4 text-center hover:border-accent-green transition-colors"
            >
              <span className="material-symbols-outlined text-accent-green">swap_horiz</span>
              <p className="text-text-secondary text-sm mt-2">New Trade</p>
            </Link>
            <Link
              to="/wallet"
              className="card-flat p-4 text-center hover:border-accent-green transition-colors"
            >
              <span className="material-symbols-outlined text-accent-green">account_balance_wallet</span>
              <p className="text-text-secondary text-sm mt-2">Deposit</p>
            </Link>
            <Link
              to="/history"
              className="card-flat p-4 text-center hover:border-accent-green transition-colors"
            >
              <span className="material-symbols-outlined text-accent-green">history</span>
              <p className="text-text-secondary text-sm mt-2">History</p>
            </Link>
            <Link
              to="/wallet"
              className="card-flat p-4 text-center hover:border-accent-green transition-colors"
            >
              <span className="material-symbols-outlined text-accent-green">logout</span>
              <p className="text-text-secondary text-sm mt-2">Withdraw</p>
            </Link>
          </div>
        </GlassCard>
      </div>
    </div>
  );
};

export default DashboardPage;