import { getSpace, type SpaceResponse } from '../api/space.ts';
import { useCallback, useEffect, useState } from 'react';
import { SpaceChannelSidebar } from '../components/SpaceChannelSidebar.tsx';
import { useRequiredParam } from '../hooks/useRequiredParam.ts';

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
                </div>
            )}
        </div>
    );
}
