import { getHappeningByUuid } from '$lib/api/happenings.js';
import { error } from '@sveltejs/kit';
import type { Actions } from './$types';

export const load = async ({ params }) => {
	const { id } = params;
	const event = await getHappeningByUuid(id);

	if (event) {
		return {
			event
		};
	}

	throw error(404, 'Event not found');
};

export const actions = {
	register: async ({ locals }) => {
		const { user } = locals;

		if (!user) {
			return {
				success: false,
				message: 'Unauthorized'
			};
		}

		return {
			success: true,
			message: 'Registered'
		};
	},
	unregister: async ({ locals }) => {
		const { user } = locals;

		if (!user) {
			return {
				success: false,
				message: 'Unauthorized'
			};
		}

		return {
			success: true,
			message: 'Unregistered'
		};
	}
} satisfies Actions;
