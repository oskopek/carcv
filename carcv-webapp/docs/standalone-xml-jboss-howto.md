##Add this security-domain to the list of security-domains in your standalone.xml to enable JAAS:

```
<security-domain name="carcv" cache-type="default">
	<authentication>
		<login-module code="UsersRoles" flag="required">
			<module-option name="usersProperties" value="users.properties" />
			<module-option name="rolesProperties" value="roles.properties" />
		</login-module>
	</authentication>
</security-domain>
```