import { defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';
import tailwindcss from '@tailwindcss/vite';

export default defineConfig(({ mode }) => {
  // 모든 VITE_ 환경변수 로드
  const env = loadEnv(mode, process.cwd(), '');

  /**
   * proxy target 우선순위
   * 1. VITE_PROXY_TARGET (docker / ci)
   * 2. VITE_API_BASE_URL (선택)
   * 3. 기본값 (로컬)
   */
  const proxyTarget =
    env.VITE_PROXY_TARGET || env.VITE_API_BASE_URL || 'http://localhost:8080';

  return {
    plugins: [react(), tailwindcss()],
    resolve: {
      alias: {
        '@': path.resolve(__dirname, './src'),
      },
    },
    server: {
      host: true, // docker dev에서 필수
      port: 5173,
      proxy: {
        '/api': {
          target: proxyTarget,
          changeOrigin: true,
          secure: false,
        },
        '/uploads': {
          target: proxyTarget,
          changeOrigin: true,
          secure: false,
        },
      },
    },
  };
});
