# CHANGES PART 3: Frontend Core Implementation

## Overview
This change adds the complete React + TypeScript frontend with the dark wireframe design system. This part depends on Parts 1 and 2 being merged first.

---

## Step-by-Step Instructions

### Step 1: Pull Latest Code
```bash
# Clone the repository (if not already cloned)
git clone https://github.com/krishank-kureti/Forex-Trader.git
cd Forex-Trader

# Pull latest changes
git pull origin main

# IMPORTANT: Merge Parts 1 and 2 first if not already done
git fetch origin
git merge origin/feature/security-enhancement
git merge origin/feature/transaction-processing
```

### Step 2: Create Branch
```bash
git checkout -b feature/frontend-core
```

### Step 3: Create Frontend Folder Structure

Create the following folder structure and files:

```
forex-frontend/
├── src/
│   ├── api/
│   │   ├── auth.ts
│   │   ├── wallet.ts
│   │   ├── trades.ts
│   │   ├── settlement.ts
│   │   ├── exchange-rate.ts
│   │   └── client.ts
│   ├── components/
│   │   ├── layout/
│   │   │   ├── Layout.tsx
│   │   │   ├── Header.tsx
│   │   │   └── Sidebar.tsx
│   │   └── common/
│   │       ├── GlassCard.tsx
│   │       └── Notification.tsx
│   ├── pages/
│   │   ├── LoginPage.tsx
│   │   ├── RegisterPage.tsx
│   │   ├── DashboardPage.tsx
│   │   ├── WalletPage.tsx
│   │   ├── TradesPage.tsx
│   │   └── HistoryPage.tsx
│   ├── hooks/
│   │   ├── useWallet.ts
│   │   ├── useTrading.ts
│   │   └── useExchangeRates.ts
│   ├── stores/
│   │   ├── authStore.ts
│   │   ├── tradeStore.ts
│   │   └── uiStore.ts
│   ├── types/
│   │   └── index.ts
│   ├── index.css
│   ├── App.tsx
│   └── main.tsx
├── index.html
├── package.json
├── tailwind.config.js
├── vite.config.ts
└── tsconfig.json
```

---

## Key Files to Create

### 1. package.json

**Location:** `forex-frontend/package.json`

```json
{
  "name": "forex-frontend",
  "private": true,
  "version": "0.0.0",
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "tsc -b && vite build",
    "lint": "eslint .",
    "preview": "vite preview"
  },
  "dependencies": {
    "@hookform/resolvers": "^5.2.2",
    "@tanstack/react-query": "^5.97.0",
    "axios": "^1.15.0",
    "react": "^19.2.4",
    "react-dom": "^19.2.4",
    "react-hook-form": "^7.72.1",
    "react-router-dom": "^7.14.0",
    "tailwindcss": "^4.2.2",
    "zod": "^4.3.6",
    "zustand": "^5.0.12"
  },
  "devDependencies": {
    "@types/react": "^19.2.14",
    "@types/react-dom": "^19.2.3",
    "@vitejs/plugin-react": "^6.0.1",
    "typescript": "~6.0.2",
    "vite": "^8.0.4"
  }
}
```

---

### 2. tailwind.config.js

**Location:** `forex-frontend/tailwind.config.js`

```javascript
/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        'bg-primary': '#0e0e0e',
        'bg-secondary': '#131313',
        'bg-surface': '#1f1f1f',
        'bg-surface-high': '#2a2a2a',
        'bg-surface-highest': '#353535',
        'accent-green': '#4ae183',
        'accent-red': '#fa5a48',
        'text-primary': '#ffffff',
        'text-secondary': '#c6c6c6',
        'text-muted': '#919191',
        'border-light': 'rgba(255, 255, 255, 0.1)',
        'border-subtle': 'rgba(255, 255, 255, 0.05)',
      },
      fontFamily: {
        'headline': ['Space Grotesk', 'sans-serif'],
        'body': ['Inter', 'sans-serif'],
        'mono': ['ui-monospace', 'SFMono-Regular', 'Menlo', 'Monaco', 'Consolas', 'monospace'],
      },
      borderRadius: {
        'none': '0px',
      },
    },
  },
  plugins: [],
}
```

---

### 3. vite.config.ts

**Location:** `forex-frontend/vite.config.ts`

```typescript
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import tailwindcss from '@tailwindcss/vite';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));

export default defineConfig({
  plugins: [react(), tailwindcss()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
});
```

---

### 4. index.html

**Location:** `forex-frontend/index.html`

```html
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link rel="icon" type="image/svg+xml" href="/favicon.svg" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Forex Trader - Trading Platform</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300;400;500;600;700;900&family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&display=swap" rel="stylesheet">
  </head>
  <body>
    <div id="root"></div>
    <script type="module" src="/src/main.tsx"></script>
  </body>
</html>
```

---

### 5. index.css (Complete Style System)

**Location:** `forex-frontend/src/index.css`

```css
@import "tailwindcss";

@import url('https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300;400;500;600;700;900&family=Inter:wght@300;400;500;600;700&display=swap');

@theme {
  --color-bg-primary: #0e0e0e;
  --color-bg-secondary: #131313;
  --color-bg-surface: #1f1f1f;
  --color-bg-surface-high: #2a2a2a;
  --color-bg-surface-highest: #353535;
  --color-accent-green: #4ae183;
  --color-accent-red: #fa5a48;
  --color-text-primary: #ffffff;
  --color-text-secondary: #c6c6c6;
  --color-text-muted: #919191;
  --color-border-light: rgba(255, 255, 255, 0.1);
  --color-border-subtle: rgba(255, 255, 255, 0.05);
  
  --font-headline: 'Space Grotesk', sans-serif;
  --font-body: 'Inter', sans-serif;
  --font-mono: 'ui-monospace', 'SFMono-Regular', 'Menlo', 'Monaco', 'Consolas', monospace;
}

* {
  border-radius: 0 !important;
}

body {
  background-color: #131313;
  color: #e2e2e2;
  font-family: 'Inter', sans-serif;
  margin: 0;
  min-height: 100vh;
}

.font-headline {
  font-family: 'Space Grotesk', sans-serif;
}

.font-mono {
  font-family: 'ui-monospace', 'SFMono-Regular', Menlo, Monaco, Consolas, monospace;
}

.blueprint-grid {
  background-image: 
    linear-gradient(to right, rgba(255, 255, 255, 0.02) 1px, transparent 1px),
    linear-gradient(to bottom, rgba(255, 255, 255, 0.02) 1px, transparent 1px);
  background-size: 40px 40px;
}

.card-flat {
  border: 1px solid rgba(255, 255, 255, 0.1);
  background: #1f1f1f;
}

.card-flat:hover {
  border-color: rgba(255, 255, 255, 0.15);
}

.input-flat {
  background: transparent;
  border: 1px solid rgba(255, 255, 255, 0.1);
  padding: 12px 16px;
  color: #ffffff;
  font-size: 14px;
  width: 100%;
  box-sizing: border-box;
  transition: border-color 0.15s ease;
}

.input-flat:focus {
  border-color: #4ae183;
  outline: none;
}

.input-flat::placeholder {
  color: #919191;
}

.status-badge {
  padding: 4px 8px;
  font-family: 'Space Grotesk', sans-serif;
  font-size: 9px;
  font-weight: 700;
  letter-spacing: 0.1em;
  text-transform: uppercase;
  border: 1px solid;
}

.status-badge.success {
  border-color: rgba(74, 225, 131, 0.3);
  color: #4ae183;
}

.status-badge.pending {
  border-color: rgba(255, 255, 255, 0.2);
  color: rgba(255, 255, 255, 0.4);
}

.status-badge.error {
  border-color: rgba(250, 90, 72, 0.3);
  color: #fa5a48;
}

::-webkit-scrollbar {
  width: 4px;
  height: 4px;
}

::-webkit-scrollbar-track {
  background: #0e0e0e;
}

::-webkit-scrollbar-thumb {
  background: #353535;
}

.material-symbols-outlined {
  font-variation-settings: 'FILL' 0, 'wght' 300, 'GRAD' 0, 'opsz' 24;
  font-size: 20px;
}

.status-dot.online {
  background: #4ae183;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}
```

---

### 6. Main App Files

**forex-frontend/src/main.tsx:**
```tsx
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import App from './App';
import './index.css';

const queryClient = new QueryClient();

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </QueryClientProvider>
  </React.StrictMode>
);
```

**forex-frontend/src/App.tsx:**
```tsx
import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/layout/Layout';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import WalletPage from './pages/WalletPage';
import TradesPage from './pages/TradesPage';
import HistoryPage from './pages/HistoryPage';

function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/" element={<Layout />}>
        <Route index element={<DashboardPage />} />
        <Route path="wallet" element={<WalletPage />} />
        <Route path="trades" element={<TradesPage />} />
        <Route path="history" element={<HistoryPage />} />
      </Route>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

export default App;
```

---

### Step 4: Commit Changes
```bash
# Add the entire forex-frontend folder
git add forex-frontend/

# Commit with a descriptive message
git commit -m "feat: Add complete React frontend with dark wireframe design system"
```

### Step 5: Push to Remote
```bash
git push origin feature/frontend-core
```

### Step 6: Create Pull Request
1. Go to https://github.com/krishank-kureti/Forex-Trader
2. Click "Compare & pull request"
3. Select `feature/frontend-core` as the compare branch
4. Add title: "Part 3: Frontend Core Implementation"
5. Add description: "Adds complete React + TypeScript frontend with dark wireframe design, API integration, and state management"
6. Click "Create pull request"

---

## Summary of This Part

This part adds:
- Complete React 18 + TypeScript frontend
- Dark minimalist wireframe design system
- Tailwind CSS v4 configuration
- Vite build tool with API proxy
- Core layout components (Header, Sidebar, Layout)
- Routing setup with React Router
- Zustand for state management
- Axios for API communication
- React Query for server state

---

## Important Notes
- This change depends on Parts 1 and 2 being merged first
- After merging, notify the next team member to proceed with Part 4

---

*Generated: April 15, 2026*
*Team Member: Part 3 - Frontend Core*