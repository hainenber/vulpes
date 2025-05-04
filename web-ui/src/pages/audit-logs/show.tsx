import { useTable } from "@refinedev/antd";
import { formatRelative } from "date-fns";
import { FC } from "react";
import { List, Table, Typography } from "antd";

const GITHUB_ADVISORY_DATABASE_REMOTE_URL =
    "https://github.com/github/advisory-database/commit";

interface IAuditLog {
    id: number;
    previous_commit_id: string;
    current_commit_id: string;
    created_at: string; // Date-like string
    changes: Record<string, string[]>;
}

const renderChanges = (changes: Record<string, string[]>) =>
    Object.entries(changes).map(([changeType, changeData]) => (
        <List
            header={<Typography.Text strong>{changeType}</Typography.Text>}
            dataSource={changeData}
            renderItem={(item) => <List.Item>{item}</List.Item>}
        />
    ));

export const AuditLogTable: FC = () => {
    const {
        tableQuery: { data, isLoading },
        tableProps,
    } = useTable<IAuditLog>({
        resource: "audit-logs",
        pagination: { current: 1, pageSize: 10 },
        sorters: { initial: [{ field: "createdAt", order: "desc" }] },
    });

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (!data?.data) {
        return <div>Error fetching audit logs</div>;
    }

    return (
        <div>
            <h1>Audit logs</h1>
            <Table {...tableProps} rowKey="id">
                <Table.Column
                    dataIndex="created_at"
                    title="Fetched at"
                    render={(fetchedTime) =>
                        formatRelative(new Date(fetchedTime), new Date())
                    }
                />
                <Table.Column
                    dataIndex="previous_commit_id"
                    title="Previous commit ID"
                    render={(commit) => (
                        <a
                            href={`${GITHUB_ADVISORY_DATABASE_REMOTE_URL}/${commit}`}
                        >
                            {String(commit).slice(0, 7)}
                        </a>
                    )}
                />
                <Table.Column
                    dataIndex="current_commit_id"
                    title="Current commit ID"
                    render={(commit) => (
                        <a
                            href={`${GITHUB_ADVISORY_DATABASE_REMOTE_URL}/${commit}`}
                        >
                            {String(commit).slice(0, 7)}
                        </a>
                    )}
                />
                <Table.Column
                    dataIndex="changes"
                    title="Changes"
                    render={(changes) =>
                        Object.keys(changes).length === 0
                            ? "No changes"
                            : renderChanges(changes)
                    }
                />
            </Table>
        </div>
    );
};
