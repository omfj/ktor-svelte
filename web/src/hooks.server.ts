import { PUBLIC_BACKEND_URL } from '$env/static/public';
import { userSchema } from '$lib/validators/user';
import type { Handle } from '@sveltejs/kit';

export const handle = (async ({ event, resolve }) => {
	// Get the session ID from the cookies
	const sessionId = event.cookies.get('session');
	// If there is a session ID, get the user from the backend
	if (sessionId) event.locals.user = await getUserFromSession(sessionId);
	// If there is no session ID, delete the session cookie
	if (!event.locals.user) event.cookies.delete('session');

	// Everything above is run before the route/api/page is resolved
	const response = await resolve(event);
	// Everything below is run after the route/api/page is resolved

	return response;
}) satisfies Handle;

/**
 * Gets the user from the backend using the session ID
 *
 * @param session session ID
 * @returns user object or null
 */
const getUserFromSession = async (session: string) => {
	try {
		const response = await fetch(PUBLIC_BACKEND_URL + '/session', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				session
			})
		});

		if (!response.ok) return undefined;

		const parsedUser = userSchema.parse(await response.json());

		return parsedUser;
	} catch (e) {
		console.error(e);
		return undefined;
	}
};
