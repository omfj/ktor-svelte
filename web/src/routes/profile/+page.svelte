<script lang="ts">
	import { user } from '$lib/stores/user';
</script>

<h1 class="text-3xl font-medium mb-5">Profile</h1>

<div class="flex flex-col gap-2">
	<p class="font-medium text-lg">UUID</p>
	<p>{$user?.id}</p>
</div>

<div class="flex flex-col gap-2">
	<p class="font-medium text-lg">Username</p>
	<p>{$user?.username}</p>
</div>

<div class="flex flex-col gap-2">
	<p class="font-medium text-lg">Email</p>
	<p>{$user?.email}</p>
</div>
