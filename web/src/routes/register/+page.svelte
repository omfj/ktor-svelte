<script lang="ts">
	import { register } from '$lib/stores/user';

	const onSubmit = async (e: Event) => {
		e.preventDefault();
		const form = e.target as HTMLFormElement;
		const formData = new FormData(form);

		const username = formData.get('username')?.toString();
		const email = formData.get('email')?.toString();
		const password = formData.get('password')?.toString();

		if (!username || !email || !password) {
			alert('Please fill out all fields');
			return;
		}

		const user = await register(username, email, password);

		if (user) {
			alert('Registration successful!');
		} else {
			alert('Registration failed!');
		}
	};
</script>

<h1 class="text-3xl font-medium mb-5">Register</h1>

<form class="space-y-4" on:submit={onSubmit}>
	<div class="flex flex-col gap-2">
		<label for="username">Username</label>
		<input
			type="text"
			name="username"
			id="username"
			class="border border-gray-300 rounded-md p-2"
		/>
	</div>

	<div class="flex flex-col gap-2">
		<label for="email">Email</label>
		<input type="email" name="email" id="email" class="border border-gray-300 rounded-md p-2" />
	</div>

	<div class="flex flex-col gap-2">
		<label for="password">Password</label>
		<input
			type="password"
			name="password"
			id="password"
			class="border border-gray-300 rounded-md p-2"
		/>
	</div>

	<div class="flex flex-col gap-2">
		<label for="passwordConfirm">Confirm Password</label>
		<input
			type="password"
			name="passwordConfirm"
			id="passwordConfirm"
			class="border border-gray-300 rounded-md p-2"
		/>
	</div>

	<div>
		<button type="submit" class="bg-blue-400 text-white rounded-md p-2">Register</button>
	</div>
</form>
