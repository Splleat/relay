import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.tsx';
import './index.css';
import { BrowserRouter, Route, Routes } from 'react-router';
import { LoginPage } from './pages/LoginPage.tsx';
import { SignUpPage } from './pages/SignUpPage.tsx';
import { RequireAuth } from './components/RequireAuth.tsx';
import { RequireGuest } from './components/RequireGuest.tsx';
import { SpacePage } from './pages/SpacePage.tsx';

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <BrowserRouter>
            <Routes>
                <Route element={<RequireGuest />}>
                    <Route path="login" element={<LoginPage />} />
                    <Route path="signup" element={<SignUpPage />} />
                </Route>
                <Route element={<RequireAuth />}>
                    <Route path="/" element={<App />}>
                        <Route path="spaces/:spaceId" element={<SpacePage />} />
                    </Route>
                </Route>
            </Routes>
        </BrowserRouter>
    </StrictMode>,
);
