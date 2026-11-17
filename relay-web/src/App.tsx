import { LogoutButton } from './components/auth/LogoutButton.tsx';
import { SpaceSidebar } from './components/space/SpaceSidebar.tsx';
import { Outlet } from 'react-router';

function App() {
    return (
        <div className="flex flex-col gap-2">
            <div className="flex flex-row gap-2">
                <h1 className="text-2xl font-bold text-blue-600">Relay</h1>
                <LogoutButton />
            </div>
            <div>
                <SpaceSidebar />
            </div>
            <Outlet />
        </div>
    );
}

export default App;
