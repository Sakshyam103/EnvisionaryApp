import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
    base: "/src/main/frontend",
  optimizeDeps:{
      entries: ['src/main/frontend/src/main.jsx'],
  },
  server: {
    port: 3000
  },
   build: {
    outDir: "public"
   }
})
