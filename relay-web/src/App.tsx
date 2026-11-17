import { LogoutButton } from './components/auth/LogoutButton.tsx';
import { SpaceSidebar } from './components/space/SpaceSidebar.tsx';
import { Outlet } from 'react-router';

function App() {
    return (
        <div className="flex flex-col">
            <div className="flex min-h-0">
                <SpaceSidebar />
                <main className="flex-1">
                    <Outlet />
                </main>
            </div>
            <div className="flex">
                <LogoutButton />
            </div>
        </div>
    );
}

export default App;
