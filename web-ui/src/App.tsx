import { Refine } from "@refinedev/core";

import { dataProvider } from "./providers/data-provider";
import { ShowAuditLog } from "./pages/audit-logs/show";

export default function App(): JSX.Element {
  return (
    <Refine dataProvider={dataProvider}>
      <ShowAuditLog/>
    </Refine>
  )
};
