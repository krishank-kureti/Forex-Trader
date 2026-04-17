import React from 'react';
import { Link } from 'react-router-dom';
import { useAuthStore } from '../../stores/authStore';

const Header: React.FC = () => {
  const { user, logout } = useAuthStore();

  return (
    <header className="h-16 border-b border-border-light bg-bg-secondary flex items-center justify-between px-6">
      <div className="flex items-center gap-4">
        <Link to="/" className="font-headline text-xl font-bold text-text-primary">
          FOREX TRADER
        </Link>
      </div>
      <div className="flex items-center gap-4">
        <div className="flex items-center gap-2">
          <span className="status-dot online w-2 h-2 rounded-full"></span>
          <span className="text-text-muted text-sm">System Online</span>
        </div>
        <div className="h-8 w-px bg-border-light"></div>
        <span className="text-text-secondary text-sm">{user?.email}</span>
        <button
          onClick={logout}
          className="text-text-muted hover:text-text-primary text-sm transition-colors"
        >
          Logout
        </button>
      </div>
    </header>
  );
};

export default Header;