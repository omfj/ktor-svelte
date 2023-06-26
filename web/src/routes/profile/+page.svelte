<script lang="ts">
	import type { PageServerData } from './$types';

	export let data: PageServerData;
</script>

<h1 class="text-3xl font-medium mb-10">Profile</h1>

<div class="space-y-4">
	<div>
		<p class="text-xs">UUID:</p>
		<p class="text-lg">{data.user.id}</p>
	</div>

	<div>
		<p class="text-xs">Username:</p>
		<p class="text-lg">{data.user.username}</p>
	</div>

	<div>
		<p class="text-xs">Email:</p>
		<p class="text-lg">{data.user.email}</p>
	</div>
</div>
