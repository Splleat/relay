import { useActionState, useId } from 'react';
import { createSpace } from '../api/space.ts';
import { getErrorMessage } from '../api/errors.ts';
import type { FormState } from '../types/common.ts';

export function CreateSpaceForm({ onCreate }: { onCreate: () => void }) {
    const [state, action, isPending] = useActionState(
        async (_prevState: FormState, formData: FormData) => {
            try {
                const name = formData.get('name') as string;

                await createSpace({ name });

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
            <form action={action} className="flex flex-col gap-2">
                <div className="flex flex-row gap-2">
                    <label htmlFor={id}>스페이스 이름</label>
                    <input id={id} name="name" required />
                </div>
                {state.error && <p className="text-red-500">{state.error}</p>}
                <button type="submit" disabled={isPending}>
                    {isPending ? '생성 중...' : '생성'}
                </button>
            </form>
        </div>
    );
}
