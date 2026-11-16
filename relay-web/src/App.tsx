import { LogoutButton } from './components/LogoutButton.tsx';

function App() {
    return (
        <div className="flex flex-row gap-2">
            <h1 className="text-2xl font-bold text-blue-600">Relay</h1>
            <LogoutButton />
        </div>
    );
}

export default App;
