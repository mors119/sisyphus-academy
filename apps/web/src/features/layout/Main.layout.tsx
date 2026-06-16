import { Outlet } from 'react-router-dom';
import { SidebarProvider } from '@/components/ui/sidebar';
import { Header } from './header/Header.widget';
import { Sidenav } from './sidenav/Sidenav.widget';
import { Suspense } from 'react';
import { Loader } from '@/components/custom/Loader';

const Layout = () => {
  return (
    <SidebarProvider defaultOpen={false}>
      <Sidenav />
      <div className="flex flex-col h-screen overflow-hidden w-full relative bg-amber-50/30 dark:bg-black/30">
        <Header />
        <main className="h-[calc(100vh-3.5rem)] relative">
          <Suspense fallback={<Loader />}>
            <Outlet />
          </Suspense>
        </main>
      </div>
    </SidebarProvider>
  );
};

export default Layout;
