<script lang="ts">
	import { logout, user } from '$lib/stores/user';
</script>

<header class="bg-blue-400 p-3 text-white flex justify-between items-center mb-10">
	<h1 class="text-3xl font-medium">echo</h1>

	<nav>
		<ul class="flex">
			<li class="mr-3">
				<a href="/" class="hover:text-gray-200">Home</a>
			</li>
			{#if !$user}
				<li class="mr-3">
					<a href="/login" class="hover:text-gray-200">Login</a>
				</li>
				<li class="mr-3">
					<a href="/register" class="hover:text-gray-200">Register</a>
				</li>
			{/if}
			{#if $user}
				<li class="mr-3">
					<a href="/profile">Profile</a>
				</li>
				<li class="mr-3">
					<button on:click={logout}>Logout</button>
				</li>
			{/if}
		</ul>
	</nav>
</header>
