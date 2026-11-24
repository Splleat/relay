import { LogoutButton } from './components/auth/LogoutButton.tsx';
import { SpaceSidebar } from './components/space/SpaceSidebar.tsx';
import { useOutlet } from 'react-router';

function App() {
    const outlet = useOutlet();

    return (
        <div className="flex h-dvh overflow-hidden">
            <aside className="flex w-[72px] shrink-0 flex-col items-center gap-3 bg-rail py-3">
                <SpaceSidebar />
                <LogoutButton />
            </aside>
            <main className="flex min-w-0 flex-1">
                {outlet ?? (
                    <div className="flex flex-1 items-center justify-center text-sm text-muted">
                        왼쪽에서 스페이스를 선택하세요.
                    </div>
                )}
            </main>
        </div>
    );
}

export default App;
