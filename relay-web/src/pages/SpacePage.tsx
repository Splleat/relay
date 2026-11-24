import { getSpace, type SpaceResponse } from '../api/space.ts';
import { useCallback, useEffect, useState } from 'react';
import { SpaceChannelSidebar } from '../components/space/SpaceChannelSidebar.tsx';
import { useRequiredParam } from '../hooks/useRequiredParam.ts';
import { useOutlet } from 'react-router';

export function SpacePage() {
    const [space, setSpace] = useState<SpaceResponse | null>(null);
    const spaceId = useRequiredParam('spaceId');
    const outlet = useOutlet();

    const loadSpace = useCallback(() => {
        getSpace(spaceId).then(setSpace);
    }, [spaceId]);

    useEffect(() => {
        loadSpace();
    }, [loadSpace]);

    if (!space) {
        return null;
    }

    return (
        <div className="flex min-w-0 flex-1">
            <aside className="flex w-60 shrink-0 flex-col border-r border-border bg-sidebar">
                <header className="flex h-14 shrink-0 items-center border-b border-border px-4">
                    <h2 className="truncate font-semibold">{space.name}</h2>
                </header>
                <SpaceChannelSidebar
                    channels={space.channels}
                    spaceId={spaceId}
                    onSuccess={loadSpace}
                />
            </aside>
            <section className="flex min-w-0 flex-1 flex-col">
                {outlet ?? (
                    <div className="flex flex-1 items-center justify-center text-sm text-muted">
                        채널을 선택하세요.
                    </div>
                )}
            </section>
        </div>
    );
}
