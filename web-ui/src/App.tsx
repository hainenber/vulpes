import { Refine } from "@refinedev/core";

import { dataProvider } from "./providers/data-provider";
import { AuditLogTable } from "./pages/audit-logs/show";
import { JSX } from "react";
import { ConfigProvider, App as AntdApp } from "antd";

export default function App(): JSX.Element {
    return (
        <ConfigProvider>
            <AntdApp>
                <Refine dataProvider={dataProvider}>
                    <AuditLogTable />
                </Refine>
            </AntdApp>
        </ConfigProvider>
    );
}
