/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./index.html","./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        primary:  { DEFAULT:"#7245ff", dark:"#5f37e3" },
        surface:  "#ffffff",
        onSurface:"#1f2937",
        muted:    "#6b7280",
      }
    },
  },
  plugins: [],
};
