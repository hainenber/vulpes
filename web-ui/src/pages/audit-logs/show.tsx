import { useList } from "@refinedev/core";

export const ShowAuditLog = () => {
    const { data, isLoading } = useList({ resource: "audit-logs" });

    if (isLoading) {
        return <div>Loading...</div>
    }

    if (!data?.data) {
        return <div>Error fetching audit logs</div>;
    }

    const auditLogs = data.data.map(i => (
        <tr key={i.id}>
            <td>{i.id}</td>
            <td>
                <a href={`https://github.com/github/advisory-database/commit/${i.previous_commit_id}`}>
                    {String(i.previous_commit_id).slice(0, 7)}
                </a>
            </td>
            <td>
                <a href={`https://github.com/github/advisory-database/commit/${i.current_commit_id}`}>
                    {String(i.current_commit_id).slice(0, 7)}
                </a>
            </td>
            <td>{new Date(i.created_at).toString()}</td>
            <td>{Object.keys(i.changes).length === 0 ? 'No changes' : JSON.stringify(i.changed_states, null, 2)}</td>
        </tr>
    ));

    return (
        <div>
            <h1>Audit logs</h1>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Previous commit ID</th>
                    <th>Current commit ID</th>
                    <th>Fetched on</th>
                    <th>Changes</th>
                </tr>
                {auditLogs}
            </table>
        </div>
    );
}