import './loader.css';

const Loader = ({state}) => {
    
    const fadeDescition = state ? 'fade-in' : 'fade-out';
    return (
        <div className={`loader  ${fadeDescition}` }>
            <div className="gooey">
                <span className="dot"></span>
                <div className="dots">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>
        </div>
    )
}

export default Loader
