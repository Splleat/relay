import type { SpaceChannelSummaryResponse } from '../../api/space.ts';
import { Link } from 'react-router';
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
        <div className="flex flex-col gap-2">
            {channels &&
                channels.map((channel) => (
                    <Link to={`/channels/${channel.id}`} key={channel.id}>
                        <div className="flex flex-row gap-2">
                            <p>{channel.name}</p>
                        </div>
                    </Link>
                ))}
            <CreateSpaceChannelButton onSuccess={onSuccess} spaceId={spaceId} />
        </div>
    );
}
