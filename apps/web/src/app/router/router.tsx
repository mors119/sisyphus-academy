import React, { Suspense, lazy } from 'react';
import { createBrowserRouter } from 'react-router-dom';
import { PATHS } from './paths.constants';
import { ProtectedRoute } from './ProtectedRoute.component';

// Lazy components
const Layout = lazy(() => import('@/features/layout/Main.layout'));
const AuthLayout = lazy(() => import('@/features/auth/Auth.layout'));

const Signin = lazy(() => import('@/features/auth/signin/Signin.page'));
const Signup = lazy(() => import('@/features/auth/signup/Signup.page'));

const OauthSuccessPage = lazy(
  () => import('@/features/auth/pages/OauthSuccess.page'),
);
const LinkHandlerPage = lazy(
  () => import('@/features/auth/pages/LinkHandler.page'),
);

const NotFoundPage = lazy(() => import('@/features/not-found/NotFound.page')); // ← 여기 파일명/경로 꼭 확인
const HomePage = lazy(() => import('@/features/home/Home.page'));

// 타입 캐스팅은 불필요하면 제거 (대부분 default export면 그대로 가능)
const UserPage = lazy(() => import('@/features/user/User.page'));
const AddPage = lazy(() => import('@/features/add/Add.page'));
const ViewPage = lazy(() => import('@/features/view/View.page'));
const TagPage = lazy(() => import('@/features/tag/Tag.page'));
const CategoryPage = lazy(() => import('@/features/category/Category.page'));
const QuickEditPage = lazy(
  () => import('@/features/quick_edit/QuickEdit.page'),
);
const RequirePage = lazy(() => import('@/features/require/Require.page'));
const RequireViewPage = lazy(
  () => import('@/features/require/require-view/RequireView.page'),
);
const DashboardPage = lazy(() => import('@/features/dashboard/Dashboard.page'));
const PolicyPage = lazy(() => import('@/features/policy/Policy.page'));

function LazyBoundary({ children }: { children: React.ReactNode }) {
  return <Suspense fallback={null}>{children}</Suspense>;
}

export const router = createBrowserRouter([
  {
    path: PATHS.AUTH,
    element: (
      <LazyBoundary>
        <AuthLayout />
      </LazyBoundary>
    ),
    children: [
      {
        path: PATHS.SIGN_IN,
        element: (
          <LazyBoundary>
            <Signin />
          </LazyBoundary>
        ),
      },
      {
        path: PATHS.SIGN_UP,
        element: (
          <LazyBoundary>
            <Signup />
          </LazyBoundary>
        ),
      },
    ],
  },
  {
    path: PATHS.OAUTH,
    element: (
      <LazyBoundary>
        <OauthSuccessPage />
      </LazyBoundary>
    ),
  },
  {
    path: PATHS.LINK,
    element: (
      <LazyBoundary>
        <LinkHandlerPage />
      </LazyBoundary>
    ),
  },
  {
    path: PATHS.POLICY,
    element: (
      <LazyBoundary>
        <PolicyPage />
      </LazyBoundary>
    ),
  },
  {
    path: PATHS.HOME,
    element: (
      <LazyBoundary>
        <Layout />
      </LazyBoundary>
    ),
    errorElement: (
      <LazyBoundary>
        <NotFoundPage />
      </LazyBoundary>
    ),
    children: [
      {
        index: true,
        element: (
          <LazyBoundary>
            <HomePage />
          </LazyBoundary>
        ),
      },
      {
        path: PATHS.ADD,
        element: (
          <ProtectedRoute>
            <LazyBoundary>
              <AddPage />
            </LazyBoundary>
          </ProtectedRoute>
        ),
      },
      {
        path: PATHS.QUICKEDIT,
        element: (
          <ProtectedRoute>
            <LazyBoundary>
              <QuickEditPage />
            </LazyBoundary>
          </ProtectedRoute>
        ),
      },
      {
        path: PATHS.CATEGORY,
        element: (
          <ProtectedRoute>
            <LazyBoundary>
              <CategoryPage />
            </LazyBoundary>
          </ProtectedRoute>
        ),
      },
      {
        path: PATHS.TAG,
        element: (
          <ProtectedRoute>
            <LazyBoundary>
              <TagPage />
            </LazyBoundary>
          </ProtectedRoute>
        ),
      },
      {
        path: PATHS.USER,
        element: (
          <ProtectedRoute>
            <LazyBoundary>
              <UserPage />
            </LazyBoundary>
          </ProtectedRoute>
        ),
      },
      {
        path: PATHS.REQUIRE,
        element: (
          <ProtectedRoute>
            <LazyBoundary>
              <RequirePage />
            </LazyBoundary>
          </ProtectedRoute>
        ),
      },
      {
        path: `${PATHS.REQUIRE}/:id`,
        element: (
          <ProtectedRoute>
            <LazyBoundary>
              <RequireViewPage />
            </LazyBoundary>
          </ProtectedRoute>
        ),
      },
      {
        path: PATHS.DASHBOARD,
        element: (
          <ProtectedRoute>
            <LazyBoundary>
              <DashboardPage />
            </LazyBoundary>
          </ProtectedRoute>
        ),
      },
      {
        path: PATHS.VIEW,
        element: (
          <ProtectedRoute>
            <LazyBoundary>
              <ViewPage />
            </LazyBoundary>
          </ProtectedRoute>
        ),
      },
      {
        path: '*',
        element: (
          <LazyBoundary>
            <NotFoundPage />
          </LazyBoundary>
        ),
      },
    ],
  },
]);
