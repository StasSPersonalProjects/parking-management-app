import Header from "./Header";

export default function PageContent({ title, children }) {

  return (
    <div>
      <Header title={title} />
      {children}
    </div>
  );
}