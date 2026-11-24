import type { SpaceChannelSummaryResponse } from '../../api/space.ts';
import { NavLink } from 'react-router';
import { CreateSpaceChannelButton } from './CreateSpaceChannelButton.tsx';

export function SpaceChannelSidebar({
    spaceId,
    channels,
    onSuccess,
}: {
    spaceId: string;
    channels: SpaceChannelSummaryResponse[];
    onSuccess: () => void;
}) {
    return (
        <div className="flex min-h-0 flex-1 flex-col px-2 py-3">
            <div className="mb-1 flex items-center justify-between px-2">
                <span className="text-xs font-semibold text-muted">채널</span>
                <CreateSpaceChannelButton
                    onSuccess={onSuccess}
                    spaceId={spaceId}
                />
            </div>
            <nav className="flex flex-col gap-0.5 overflow-y-auto">
                {channels &&
                    channels.map((channel) => (
                        <NavLink
                            to={`/spaces/${spaceId}/channels/${channel.id}`}
                            key={channel.id}
                            className={({ isActive }) =>
                                `flex items-center gap-2 rounded-md px-2 py-1.5 text-sm transition-colors ${
                                    isActive
                                        ? 'bg-accent-soft font-medium text-accent'
                                        : 'text-fg/80 hover:bg-hover hover:text-fg'
                                }`
                            }
                        >
                            <span className="text-muted">#</span>
                            <span className="truncate">{channel.name}</span>
                        </NavLink>
                    ))}
            </nav>
        </div>
    );
}
