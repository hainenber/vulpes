import { useTable } from "@refinedev/core";
import { formatRelative } from "date-fns";

const GITHUB_ADVISORY_DATABASE_REMOTE_URL =
    "https://github.com/github/advisory-database/commit";

export const ShowAuditLog = () => {
    const {
        tableQuery: { data, isLoading },
    } = useTable({
        resource: "audit-logs",
        pagination: { current: 1, pageSize: 10 },
        sorters: { initial: [{ field: "createdAt", order: "desc" }] },
    });

    if (isLoading) {
        return <div>Loading...</div>;
    }

    // @ts-ignore
    if (!data?.data?.content) {
        return <div>Error fetching audit logs</div>;
    }

    // @ts-ignore
    const auditLogs = data.data.content.map((i) => (
        <tr key={i.id}>
            <td>
                {formatRelative(new Date(i.created_at).valueOf(), new Date())}
            </td>
            <td>
                <a
                    href={`${GITHUB_ADVISORY_DATABASE_REMOTE_URL}/${i.previous_commit_id}`}
                >
                    {String(i.previous_commit_id).slice(0, 7)}
                </a>
            </td>
            <td>
                <a
                    href={`${GITHUB_ADVISORY_DATABASE_REMOTE_URL}/${i.current_commit_id}`}
                >
                    {String(i.current_commit_id).slice(0, 7)}
                </a>
            </td>
            <td>
                {Object.keys(i.changes).length === 0
                    ? "No changes"
                    : JSON.stringify(i.changed_states, null, 2)}
            </td>
        </tr>
    ));

    return (
        <div>
            <h1>Audit logs</h1>
            <table>
                <thead>
                    <tr>
                        <th>Fetched on</th>
                        <th>Previous commit ID</th>
                        <th>Current commit ID</th>
                        <th>Changes</th>
                    </tr>
                </thead>
                <tbody>{auditLogs}</tbody>
            </table>
        </div>
    );
};
