import { useActionState, useId } from 'react';
import type { FormState } from '../../types/common.ts';
import { createSpaceChannel } from '../../api/space.ts';
import { getErrorMessage } from '../../api/errors.ts';

export function CreateSpaceChannelForm({
    spaceId,
    onCreate,
}: {
    spaceId: string;
    onCreate: () => void;
}) {
    const [state, action, isPending] = useActionState(
        async (_prevState: FormState, formData: FormData) => {
            const name = formData.get('name') as string;

            try {
                await createSpaceChannel(spaceId, { name });

                onCreate();

                return { error: null };
            } catch (error) {
                const errorMessage = getErrorMessage(error);

                return { error: errorMessage };
            }
        },
        { error: null },
    );

    const id = useId();

    return (
        <div>
            <h2 className="text-lg font-semibold">새 채널</h2>
            <form action={action} className="mt-4 flex flex-col gap-4">
                <div>
                    <label htmlFor={id} className="field-label">
                        채널 이름
                    </label>
                    <input id={id} name="name" required className="input" />
                </div>
                {state.error && <p className="form-error">{state.error}</p>}
                <button
                    type="submit"
                    disabled={isPending}
                    className="btn-primary self-end"
                >
                    {isPending ? '생성 중...' : '생성'}
                </button>
            </form>
        </div>
    );
}
