import { PUBLIC_BACKEND_URL } from '$env/static/public';
import { z } from 'zod';

export const happeningSchema = z.object({
	id: z.string(),
	title: z.string(),
	date: z.string()
});
export type Happening = z.infer<typeof happeningSchema>;

export const getHappenings = async () => {
	try {
		const happenings = await fetch(PUBLIC_BACKEND_URL + '/happening', {
			method: 'GET'
		});

		if (!happenings.ok) return null;

		return happeningSchema.array().parse(await happenings.json());
	} catch (e) {
		console.error(e);
		return null;
	}
};

export const getHappeningByUuid = async (uuid: string) => {
	try {
		const happening = await fetch(PUBLIC_BACKEND_URL + '/happening/' + uuid, {
			method: 'GET'
		});

		if (!happening.ok) return null;

		return happeningSchema.parse(await happening.json());
	} catch (e) {
		console.error(e);
		return null;
	}
};
