import { useEffect, useState } from 'react';
import { getJoinedSpaces, type JoinedSpacesResponse } from '../../api/space.ts';
import { Link } from 'react-router';
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
        <div className="flex flex-col gap-2">
            {spaces?.spaces &&
                spaces.spaces.map((space) => (
                    <Link to={`/spaces/${space.id}`} key={space.id}>
                        <div className="flex flex-row gap-2">
                            <p>{space.name}</p>
                        </div>
                    </Link>
                ))}
            <CreateSpaceButton onSuccess={loadJoinedSpaces} />
        </div>
    );
}
