import "./Pagination.css";

interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

const Pagination = ({
  currentPage,
  totalPages,
  onPageChange,
}: PaginationProps) => {
  const pages = Array.from({ length: totalPages }, (_, i) => i + 1);

  return (
    <nav aria-label="Page navigation" className="paginationNav">
      <ul className="pagination">
        <li className={`pageItem ${currentPage === 0 ? "disabled" : ""}`}>
          <button
            className="pageLink"
            onClick={() => onPageChange(currentPage - 1)}
            disabled={currentPage === 0}
          >
            Anterior
          </button>
        </li>
        {pages.map((page) => (
          <li
            key={page}
            className={`pageItem ${currentPage === page - 1 ? "active" : ""}`}
          >
            <button className="pageLink" onClick={() => onPageChange(page - 1)}>
              {page}
            </button>
          </li>
        ))}
        <li
          className={`pageItem ${
            currentPage === totalPages - 1 ? "disabled" : ""
          }`}
        >
          <button
            className="pageLink"
            onClick={() => onPageChange(currentPage + 1)}
            disabled={currentPage === totalPages - 1}
          >
            Próximo
          </button>
        </li>
      </ul>
    </nav>
  );
};

export default Pagination;
