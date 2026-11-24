import { useEffect, useState } from 'react';
import { getJoinedSpaces, type JoinedSpacesResponse } from '../../api/space.ts';
import { NavLink } from 'react-router';
import { CreateSpaceButton } from './CreateSpaceButton.tsx';

export function SpaceSidebar() {
    const [spaces, setSpaces] = useState<JoinedSpacesResponse | null>(null);

    const loadJoinedSpaces = () => {
        getJoinedSpaces().then(setSpaces);
    };

    useEffect(() => {
        loadJoinedSpaces();
    }, []);

    return (
        <nav className="flex min-h-0 flex-1 flex-col items-center gap-2 overflow-y-auto">
            {spaces?.spaces &&
                spaces.spaces.map((space) => (
                    <NavLink
                        to={`/spaces/${space.id}`}
                        key={space.id}
                        title={space.name}
                        className={({ isActive }) =>
                            `flex size-11 shrink-0 items-center justify-center font-semibold transition-all ${
                                isActive
                                    ? 'rounded-xl bg-accent text-accent-fg'
                                    : 'rounded-2xl bg-rail-item text-rail-fg hover:rounded-xl hover:text-white'
                            }`
                        }
                    >
                        {space.name.at(0)}
                    </NavLink>
                ))}
            <CreateSpaceButton onSuccess={loadJoinedSpaces} />
        </nav>
    );
}
