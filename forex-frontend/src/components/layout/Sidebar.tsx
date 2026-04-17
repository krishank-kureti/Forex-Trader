import React from 'react';
import { NavLink } from 'react-router-dom';

const Sidebar: React.FC = () => {
  const navItems = [
    { path: '/', label: 'Dashboard', icon: 'dashboard' },
    { path: '/wallet', label: 'Wallet', icon: 'account_balance_wallet' },
    { path: '/trades', label: 'Trades', icon: 'swap_horiz' },
    { path: '/history', label: 'History', icon: 'history' },
  ];

  return (
    <aside className="w-64 bg-bg-primary border-r border-border-light min-h-screen">
      <div className="p-6">
        <h1 className="font-headline text-2xl font-bold text-text-primary tracking-wider">
          FX
        </h1>
        <p className="text-text-muted text-xs mt-1">TRADING PLATFORM</p>
      </div>
      <nav className="mt-6">
        {navItems.map((item) => (
          <NavLink
            key={item.path}
            to={item.path}
            className={({ isActive }) =>
              `flex items-center gap-3 px-6 py-3 text-sm transition-colors ${
                isActive
                  ? 'text-accent-green border-l-2 border-accent-green bg-bg-surface'
                  : 'text-text-secondary hover:text-text-primary hover:bg-bg-surface'
              }`
            }
          >
            <span className="material-symbols-outlined">{item.icon}</span>
            {item.label}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
};

export default Sidebar;