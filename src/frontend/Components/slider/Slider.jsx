import './slider.scss';
import { useEffect, useState } from 'react';
import { KeyboardArrowLeftIcon, KeyboardArrowRightIcon } from '../../assets/index';
import { noData } from '../../assets/index';
const Slider = ({ slides }) => {

    const [currentImageIndex, setCurrentImageIndex] = useState(0);



    const nextSlide = () => {
        if (slides.length === 0)
            return;
        const lastIndex = slides.length - 1;
        const shouldResetIndex = currentImageIndex === lastIndex;
        const index = shouldResetIndex ? 0 : currentImageIndex + 1;
        setCurrentImageIndex(index);
        console.log(currentImageIndex);
    }

    const prevSlide = () => {
        if (slides.length === 0)
            return;
        const lastIndex = slides.length - 1;
        const shouldResetIndex = currentImageIndex === 0;
        const index = shouldResetIndex ? lastIndex : currentImageIndex - 1;
        setCurrentImageIndex(index);
    }



    useEffect(() => {
        const interval = setInterval(() => {
            nextSlide();
        }, 5000);
        return () => clearInterval(interval);

    }, [currentImageIndex])

    return (
        <div className="slider">
            <div
                className="slider__inner"
                style={{ transform: `translateX(-${currentImageIndex * 100}%)` }}
            >
                {
                    slides.length === 0 ?
                        <div className='slider__inner__slide'>
                            <img src={noData} alt="" />
                        </div>
                        :
                        slides.map((slide, index) => (

                            <div
                                key={index}
                                className='slider__inner__slide'

                            >
                                <img src={slide.image} alt="" />
                                <div className="slider__inner__slide__description">
                                    <span>{slide.title || "No Data"}</span>
                                </div>
                            </div>))
                }
            </div>
            <div className="slider__navigation">
                <KeyboardArrowLeftIcon
                    className='slider__navigation__prev navigation__icon'
                    onClick={prevSlide} />
                <KeyboardArrowRightIcon
                    className='slider__navigation__next navigation__icon'
                    onClick={nextSlide} />
            </div>
        </div>
    )
}


export default Slider;