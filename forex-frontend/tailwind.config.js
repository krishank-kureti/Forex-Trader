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