import { getSpace, type SpaceResponse } from '../api/space.ts';
import { useCallback, useEffect, useState } from 'react';
import { SpaceChannelSidebar } from '../components/space/SpaceChannelSidebar.tsx';
import { useRequiredParam } from '../hooks/useRequiredParam.ts';
import { Outlet } from 'react-router';

export function SpacePage() {
    const [space, setSpace] = useState<SpaceResponse | null>(null);
    const spaceId = useRequiredParam('spaceId');

    const loadSpace = useCallback(() => {
        getSpace(spaceId).then(setSpace);
    }, [spaceId]);

    useEffect(() => {
        loadSpace();
    }, [loadSpace]);

    return (
        <div>
            {space && (
                <div className="flex flex-row gap-2">
                    <SpaceChannelSidebar
                        channels={space.channels}
                        spaceId={spaceId}
                        onSuccess={loadSpace}
                    />
                    <Outlet />
                </div>
            )}
        </div>
    );
}
